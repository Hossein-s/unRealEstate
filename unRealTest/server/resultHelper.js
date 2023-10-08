import path from "path";
import { fileURLToPath } from "url";
import fs from "fs/promises";
import objectPath from "object-path";

export async function getResults() {
  const resultsPath = path.join(fileURLToPath(import.meta.url), "../..", "results");
  const resultFiles = await fs.readdir(resultsPath);
  const results = await Promise.all(resultFiles.map(async (file) => {
    const result = await fs.readFile(path.join(resultsPath, file), { encoding: "utf-8" });
    const data = JSON.parse(result);
    data.meta = extractMeta(file);

    return data;
  }));

  return ["create-agents-test", "get-agents-test", "create-properties-test"]
    .map((scenario) => {
      return {
        name: scenario,
        parameters: ["metrics.iterations.rate", "metrics.http_req_duration.p(95)"]
          .map((property) => ({
            name: property,
            cases: [50, 100, 200, 400]
              .map((vus) => ({
                vus,
                ...Object.fromEntries(["Blocking", "NonBlocking-Coroutines", "NonBlocking-WebFlux"].map((project) => {
                  return [fixProjectCase(project), getAverageValue(
                    results,
                    scenario,
                    project,
                    vus,
                    property
                  )];
                }))
              }))
          }))
      };
    });
}

function extractMeta(fileName) {
  const regex = /output-unRealEstate-(Blocking|NonBlocking-[A-Za-z]+)-([A-Za-z-]+)-(\d+)-(\d+).json/;
  const [, project, scenario, vus, iteration] = fileName.match(regex);

  return {
    project,
    scenario,
    vus: parseInt(vus),
    iteration: parseInt(iteration)
  };
}

function getAverageValue(results, scenario, project, vus, property) {
  const filteredResults = results.filter((result) => {
    return result.meta.scenario === scenario &&
      result.meta.project === project &&
      result.meta.vus === vus;
  });

  return filteredResults.reduce((acc, result) => {
    return acc + objectPath.get(result, property);
  }, 0) / filteredResults.length;
}

function fixProjectCase(project) {
  return project[0].toLowerCase() + project.replace("-", "").slice(1);
}
