import axios from "axios";

const FILE_SERVER_BASE_API = "http://localhost:4514";

class FileServerEndpoints {
  signup(user) {
    return axios.post(FILE_SERVER_BASE_API + "/auth/register", user);
  }

  login(user) {
    return axios.post(FILE_SERVER_BASE_API + "/auth/login", user);
  }

  forgotPassword(user) {
    return axios.post(FILE_SERVER_BASE_API + "/auth/forgot-password", user);
  }

  updatePassword(token, user) {
    return axios.post(
      FILE_SERVER_BASE_API + "/auth/update-password?token=" + token,
      user
    );
  }

  changePassword(userPassword, user) {
    return axios.post(
      FILE_SERVER_BASE_API + "/auth/change-password",
      userPassword,
      {
        headers: {
          Authorization: `Bearer ${user.jwt}`,
        },
      }
    );
  }

  shareFile(fileShareBody, user) {
    return axios.post(FILE_SERVER_BASE_API + "/file/share", fileShareBody, {
      headers: {
        Authorization: `Bearer ${user.jwt}`,
      },
    });
  }

  downloadFile(fileId, user) {
    return axios.get(FILE_SERVER_BASE_API + "/file/download/" + fileId, {
      responseType: "blob",
      headers: {
        Authorization: `Bearer ${user.jwt}`,
      },
    });
  }

  uploadFile(fileBody, user) {
    return axios.post(FILE_SERVER_BASE_API + `/file/upload`, fileBody, {
      headers: {
        "Content-Type": "multipart/form-data",
        Authorization: `Bearer ${user.jwt}`,
      },
    });
  }

  adminGetAllFiles(user) {
    return axios.get(FILE_SERVER_BASE_API + "/file/admin/all-files", {
      headers: {
        Authorization: `Bearer ${user.jwt}`,
      },
    });
  }

  userGetAllFiles(user) {
    return axios.get(FILE_SERVER_BASE_API + "/file/user/all-files", {
      headers: {
        Authorization: `Bearer ${user.jwt}`,
      },
    });
  }

  deleteFileById(fileId, user) {
    return axios.delete(FILE_SERVER_BASE_API + "/file/delete/" + fileId, {
      headers: {
        Authorization: `Bearer ${user.jwt}`,
      },
    });
  }

  adminSearchForFile(fileName, user) {
    return axios.get(
      FILE_SERVER_BASE_API + "/file/admin/search-for-file/" + fileName,
      {
        headers: {
          Authorization: `Bearer ${user.jwt}`,
        },
      }
    );
  }

  userSearchForFile(fileName, user) {
    return axios.get(
      FILE_SERVER_BASE_API + "/file/user/search-for-file/" + fileName,
      {
        headers: {
          Authorization: `Bearer ${user.jwt}`,
        },
      }
    );
  }

  logout(user) {
    return axios.post(
      FILE_SERVER_BASE_API + "/logout",
      {},
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${user.jwt}`,
        },
      }
    );
  }
}

export default new FileServerEndpoints();
