import { check } from "k6";
import http from "k6/http";

export default () => {
  const url = "http://localhost:8080/agents";
  const res = http.get(url);

  check(res, {
    "status is 200": () => res.status === 200
  });
};
