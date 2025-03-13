import axios from "axios";

const FILE_SERVER_BASE_API = "http://localhost:4514";

class FileServerEndpoints {

  uploadFile(fileBody) {
    return axios.post(FILE_SERVER_BASE_API + "/files", fileBody);
  }

  adminGetAllFiles() {
    return axios.get(FILE_SERVER_BASE_API + "/files");
  }

  deleteFileById(fileId, user) {
    return axios.delete(FILE_SERVER_BASE_API + "/files/" + fileId);
  }
}

export default new FileServerEndpoints();
