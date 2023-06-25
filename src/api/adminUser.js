import http from "../utils/http/http.js";

const login = (data) => {
    return http.post("/adminUser/login", data);
};

export default {
    login,
};
