import instance from "./axios";

// 主要用来封装各种请求
const post = (url, data) => {
  console.log(url);
  return new Promise((resolve, reject) => {
    instance
      .post(url, data)
      .then((res) => {
        console.log(instance.getUri);
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

const get = (url, data) => {
  return new Promise((resolve, reject) => {
    instance
      .get(url, { params: data })
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

const put = (url, data) => {
  return new Promise((resolve, reject) => {
    instance
      .put(url, data)
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

const del = (url, data) => {
  return new Promise((resolve, reject) => {
    instance
      .delete(url, { params: data })
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

export default {
  post,
  get,
  put,
  del,
};
