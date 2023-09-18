import { check } from "k6";
import { Options } from "k6/options";
import http from "k6/http";

export let options: Options = {
  vus: 5000,
  duration: "60s",
};

export default () => {
  const url = "http://localhost:8080/agents";
  const res = http.get(url);

  check(res, {
    "status is 200": () => res.status === 200,
  });
};
