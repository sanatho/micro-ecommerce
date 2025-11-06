const http = require("node:http");
const { URLSearchParams } = require("url");

const body = new URLSearchParams({
  grant_type: "password",
  client_id: "spring-microservice-products",
  username: "thomas",
  password: "1",
});

const options = {
  hostname: "keycloak-deploy",
  port: 8080,
  method: "POST",
  path: "/realms/ecommerce/protocol/openid-connect/token",
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
    "Content-Length": Buffer.byteLength(body.toString()),
  },
};

const req = http.request(options, (res) => {
  res.setEncoding("utf8");
  res.on("data", (chunk) => {
    const obj = JSON.parse(chunk);
    console.log("🔐 Access Token: ", obj.access_token);
  })
});

// ✅ write the form-encoded body instead of appending it to the URL
req.write(body.toString());
req.end();