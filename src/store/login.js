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
      const response = await userApi.login(formData);

      this.loginUser.username = response.data.data.username;
      this.loginUser.username = response.data.data.password;

      return response;
    },
  },
});
