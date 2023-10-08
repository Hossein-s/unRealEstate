import { check } from "k6";
import http from "k6/http";

export default () => {
  const url = "http://localhost:8080/properties";
  const payload = {
    agentId: 100,
    ownerName: "John",
    state: "NY",
    city: "New York",
    address: "1st alley, right",
    price: 100500,
    description: "This is a very good property"
  };

  const params = {
    headers: {
      "Content-Type": "application/json"
    }
  };

  const res = http.post(url, JSON.stringify(payload), params);

  check(res, {
    "status is 200": () => res.status === 200
  });
};
