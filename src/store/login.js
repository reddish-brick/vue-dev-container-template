import {defineStore} from "pinia";
import adminUserApi from "../api/adminUser.js";

export const useAdminUserStore = defineStore({
    id: "adminLoginUser",
    state: () => ({
        adminLoginUser: {
            userId: "",
            username: "",
            password: "",
        },
    }),
    actions: {
        async login(formData) {
            const res = await adminUserApi.login(formData);
            return res;
        },
        storeLoginInfoToLocal(userId, username, password) {
            sessionStorage.setItem("usrId", userId);
            sessionStorage.setItem("username", username);
            sessionStorage.setItem("password", password);
        },
    },
});
