import { execSync, spawn } from "child_process";
import { fileURLToPath } from "url";
import { join } from "path";
import chalk from "chalk";

const log = console.log;
const rootDir = join(fileURLToPath(import.meta.url), "..");

main().catch(console.error);

async function main() {
  const projects = [
    "unRealEstate-Blocking",
    "unRealEstate-NonBlocking-Coroutines",
    "unRealEstate-NonBlocking-WebFlux"
  ];
  const conditions = [50, 100, 200, 400].map((vus) => ({ vus, duration: "60s" }));
  const scenario = "create-properties-test";
  const scenarioFile = join(rootDir, "scenarios", scenario + ".js");

  for (const condition of conditions) {
    for (let i = 1; i <= 4; i++) {
      for (const project of projects) {
        const outFile = `results/output-${project}-${scenario}-${condition.vus}-${i}.json`;
        await runTest(project, scenarioFile, condition, outFile);
      }
    }
  }
}

async function runTest(project, scenario, conditions, outFile) {
  const path = join(rootDir, "..", project, "docker-compose.yml");
  log("\n\n");

  log(chalk.cyan(` ⌛ Starting docker compose for ${project}...`));
  const stopDockerCompose = await startDockerCompose(path);
  log(chalk.cyan(" 🚀 Docker compose started"));

  log(chalk.cyan(` 🔬 Running k6 test for ${project}`));
  runK6(project, scenario, conditions.vus, conditions.duration, outFile);
  log(chalk.cyan(" ✅ K6 test successfully finished"));

  log(chalk.cyan(` 🧨 Stopping docker compose for ${project}`));
  stopDockerCompose();
  log(chalk.cyan(" ✅ Docker compose stopped"));

  log(chalk.cyan`Waiting 1s`);
  await delay(1000);
}

function runK6(project, testPath, vus, duration, outFile) {
  const k6Command = `k6 run -u ${vus} -d ${duration} --summary-export ${outFile} ${testPath}`;
  return execSync(k6Command, { stdio: "inherit" });
}

function startDockerCompose(path) {
  return new Promise((resolve, reject) => {
    const proc = spawn("docker", ["compose", "-f", path, "up"]);

    let output = "";
    const onDataListener = (data) => {
      output += data.toString();

      if (output.match(/Started ([A-Za-z0-9-]+)ApplicationKt in/)) {
        proc.stdout.off("data", onDataListener);
        resolve(() => execSync("docker compose -f " + path + " stop"));
      }
    };
    proc.stdout.on("data", onDataListener);
  });
}

function delay(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
