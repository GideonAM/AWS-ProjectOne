import { useState } from "react";
import FileServerEndpoints from "../service/FileServerEndpoints";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Input } from "@mantine/core";

const UpdatePassword = () => {
  const { token } = useParams();
  const [user, setUser] = useState({
    password: "",
  });

  const [passwordType, setPasswordType] = useState("password");
  const navigate = useNavigate();

  const passwordChange = (e) => {
    e.preventDefault();
    FileServerEndpoints.updatePassword(token, user)
      .then((response) => {
        alert(response.data);
        navigate("/login");
      })
      .catch((error) => {
        let message = error.response.data.password
          ? error.response.data.password
          : error.response.data;
        alert(message);
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
          <div className="items-center justify-center h-14 w-full my-4">
            <label className="font-semibold text-xl">Enter new password:</label>
            <div className="flex gap-5">
              <Input
                type={passwordType}
                name="password"
                value={user.password}
                onChange={(e) => handleChange(e)}
              ></Input>
              <span
                className="hover:cursor-pointer"
                onClick={() => setPasswordType("text")}
              >
                view 🐵
              </span>
              <span
                className="hover:cursor-pointer"
                onClick={() => setPasswordType("password")}
              >
                hide 🙈
              </span>
            </div>
          </div>
          <div className="mt-2">
            <Button onClick={passwordChange}>Send</Button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default UpdatePassword;
