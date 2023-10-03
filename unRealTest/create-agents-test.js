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
  const payload = {
    firstname: "John",
    lastname: "Doe",
    nickname: "JohnDoe",
    email: "johndoe@gmail.com",
    phone: "+43johndoe",
    password: "12345johndoe",
    birthDate: "2023-09-17",
  };
  const params = {
    headers: {
      "Content-Type": "application/json",
    },
  };

  const res = http.post(url, JSON.stringify(payload), params);

  check(res, {
    "status is 200": () => res.status === 200,
  });
};
