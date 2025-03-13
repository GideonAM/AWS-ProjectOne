import { useState } from "react";
import FileServerEndpoints from "../service/FileServerEndpoints";
import { Button, Input } from "@mantine/core";

const ChangePassword = ({ user }) => {
  const [userPassword, setUserPassword] = useState({
    currentPassword: "",
    newPassword: "",
  });
  const [message, setMessage] = useState("");

  const passwordChange = (e) => {
    e.preventDefault();
    userPassword.jwt = `${user.jwt}`;

    FileServerEndpoints.changePassword(userPassword, user)
      .then((response) => {
        alert(response.data);
      })
      .catch((error) => {
        let message = error.response.data.currentPassword
          ? error.response.data.currentPassword
          : error.response.data.newPassword
          ? error.response.data.newPassword
          : error.response.data;
        setMessage(message);
        setTimeout(() => setMessage(""), 5000);
      });
  };

  const handleChange = (e) => {
    const value = e.target.value;
    setUserPassword({ ...userPassword, [e.target.name]: value });
  };

  return (
    <div className="p-11">
      <form className="mx-auto flex border-b shadow max-w-2xl">
        <div className="p-11">
          <div className="text-xl text-red-500">{message ? message : ""}</div>
          <div className="items-center justify-center h-14 w-full my-4">
            <label className="font-semibold text-xl">
              Enter current password:
            </label>
            <Input
              type="password"
              name="currentPassword"
              value={userPassword.currentPassword}
              onChange={(e) => handleChange(e)}
            ></Input>
          </div>
          <div className="items-center justify-center h-14 w-full my-4">
            <label className="font-semibold text-xl">Enter new password:</label>
            <Input
              type="password"
              name="newPassword"
              value={userPassword.newPassword}
              onChange={(e) => handleChange(e)}
            ></Input>
          </div>
          <div className="mt-2">
            <Button onClick={passwordChange}>Send</Button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default ChangePassword;
