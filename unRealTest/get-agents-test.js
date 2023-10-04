import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { check } from "k6";
import http from "k6/http";

export function handleSummary(data) {
  return {
    "summary.html": htmlReport(data),
  };
}

export default () => {
  const url = "http://localhost:8080/agents";
  const res = http.get(url);

  check(res, {
    "status is 200": () => res.status === 200,
  });
};
