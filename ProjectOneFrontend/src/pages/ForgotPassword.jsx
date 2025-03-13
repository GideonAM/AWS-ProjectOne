import { useState } from "react";
import FileServerEndpoints from "../service/FileServerEndpoints";
import { Button, Input } from "@mantine/core";

const ForgotPassword = () => {
  const [user, setUser] = useState({
    email: "",
  });

  const [message, setMessage] = useState("");

  const passwordChange = (e) => {
    e.preventDefault();
    FileServerEndpoints.forgotPassword(user)
      .then((response) => {
        alert(response.data);
      })
      .catch((error) => {
        let message = error.response.data.email
          ? error.response.data.email
          : error.response.data;
        setMessage(message);
        setTimeout(() => setMessage(""), 5000);
      });
  };

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({ ...user, [e.target.name]: value });
  };

  return (
    <div className="p-11">
      <form className="mx-auto flex border-b shadow max-w-2xl">
        <div className="p-11">
          <div className="text-xl text-red-500">{message ? message : ""}</div>
          <div>
            <label className="font-semibold text-xl">Enter your email:</label>
            <Input
              type="email"
              name="email"
              value={user.email}
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

export default ForgotPassword;
