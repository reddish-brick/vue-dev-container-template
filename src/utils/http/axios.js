import axios from "axios";
import {ElMessage} from "element-plus";
import router from '../../router/index'
import {localGet} from '../index'

// 主要用来创建 axios 实例、拦截请求和响应

// 1. 创建axios实例
const axiosInstance = axios.create({});

// 超时时间
axiosInstance.defaults.timeout = 50000
// 这边由于后端没有区分测试和正式，姑且都写成一个接口。
axiosInstance.defaults.baseURL = import.meta.env.VITE_API_BASE_URL
// 携带 cookie，对目前的项目没有什么作用，因为我们是 token 鉴权
axiosInstance.defaults.withCredentials = true
// 请求头，headers 信息
axiosInstance.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
axiosInstance.defaults.headers['token'] = localGet('token') || ''
// 默认 post 请求，使用 application/json 形式
axiosInstance.defaults.headers.post['Content-Type'] = 'application/json'

// 2.请求拦截
axiosInstance.interceptors.request.use(
    (config) => {
        let token = sessionStorage.getItem("token");
        if (token) {
            config.headers["token"] = token;
        }
        return config;
    },
    (error) => {
        //  请求发生错误，抛出异常
        Promise.reject(error);
    }
);

// 3.响应拦截
axiosInstance.interceptors.response.use(
    (res) => {
        return res.data;
    },
    (error) => {
        if (error && error.response) {
            const status = error.response.status;
            switch (status) {
                case 400:
                    ElMessage.error("请求错误");
                    break;
                case 401:
                    ElMessage.error("未授权，请重新登录");
                    break;
                case 403:
                    ElMessage.error("拒绝访问");
                    break;
                case 404:
                    ElMessage.error("请求错误，未找到相应的资源");
                    break;
                case 408:
                    ElMessage.error("请求超时");
                    break;
                case 500:
                    ElMessage.error("服务器内部错误");
                    break;
                case 501:
                    ElMessage.error("网络未实现");
                    break;
                case 502:
                    ElMessage.error("网络错误");
                    break;
                case 503:
                    ElMessage.error("服务不可用");
                    break;
                case 504:
                    ElMessage.error("网络超时");
                    break;
                case 505:
                    ElMessage.error("HTTP版本不支持该请求");
                    break;
                case 419:
                    router.push({path: '/login'})
                    break;
                default:
                    ElMessage.error("请求失败");
            }
        } else {
            if (JSON.stringify(error).includes("timeout")) {
                ElMessage.error("服务器响应超时，请刷新页面");
            }
            ElMessage.error("连接服务器失败");
        }
        return Promise.reject(error);
    }
);
// 4.导出 axios 实例
export default axiosInstance;
