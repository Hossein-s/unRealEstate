import fastify from "fastify";
import fastifyStatic from "@fastify/static";
import path from "path";
import { fileURLToPath } from "url";
import { getResults } from "./resultHelper.js";

const server = fastify({
  logger: true
});

server.register(fastifyStatic, {
  root: path.join(path.dirname(fileURLToPath(import.meta.url)), "public")
});

server.get("/", async (request, reply) => {
  return reply.sendFile("index.html");
});

server.get("/data", async () => {
  return await getResults();
});

const start = async () => {
  try {
    await server.listen({ port: 3000 });
  } catch (err) {
    server.log.error(err);
    process.exit(1);
  }
};

start().catch(console.error);
