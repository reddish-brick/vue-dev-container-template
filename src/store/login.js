import { defineStore } from "pinia";
import userApi from "../api/user";

export const useLoginUserStore = defineStore({
  id: "loginUser",
  state: () => ({
    loginUser: {
      username: "",
      pasword: "",
    },
  }),
  actions: {
    async login(formData) {
      const res = await userApi.login(formData);
      return res;
    },
    storeLoginInfoToLocal(username, password) {
      sessionStorage.setItem("username", username);
      sessionStorage.setItem("password", password);
    },
  },
});
