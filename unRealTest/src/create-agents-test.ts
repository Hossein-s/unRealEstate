import { sleep, check } from "k6";
import { Options } from "k6/options";
import http from "k6/http";

export let options: Options = {
  vus: 1000,
  duration: "60s",
};

export default () => {
  const url = "http://localhost:8080/agents";
  const payload = {
    firstname: "John",
    lastname: "Doe",
    nickname: "JohnDoe",
    email: "johndoe@gmail.com",
    phone: "+43johndoe",
    password: "12345johndoe",
    birthDate: "2023-09-17T13:41:34.300Z",
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

  sleep(1);
};
