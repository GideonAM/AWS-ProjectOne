import { useState } from "react";
import FileServerEndpoints from "../service/FileServerEndpoints";
import { useNavigate } from "react-router-dom";
import { Button, Input } from "@mantine/core";

const Login = ({ dispatchUser }) => {
  const [user, setUser] = useState({
    email: "",
    password: "",
  });
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const loginUser = (e) => {
    e.preventDefault();
    FileServerEndpoints.login(user)
      .then((response) => {
        dispatchUser({ type: "update-session", payload: response.data });
        {
          response.data.admin ? navigate("/admin") : navigate("/user");
        }
      })
      .catch((error) => {
        let message = error.response.data.email
          ? error.response.data.email
          : error.response.data.password
          ? error.response.data.password
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
      <form className="flex max-w-lg mx-auto shadow border-b">
        <div className="px-8 py-8">
          <div className="text-2xl flex justify-center trackling-wider font-bold">
            <h1>Login to File Server</h1>
          </div>
          <div className="text-xl text-red-500">{message ? message : ""}</div>
          <div className="items-center justify-center h-14 w-full my-4">
            <label className="font-semibold text-xl">Email: </label>
            <Input
              onChange={(e) => handleChange(e)}
              name="email"
              value={user.email}
              type="email"
            ></Input>
          </div>
          <div className="items-center justify-center h-14 w-full my-4">
            <label className="font-semibold text-xl">Password:</label>
            <Input
              onChange={(e) => handleChange(e)}
              name="password"
              value={user.password}
              type="password"
            ></Input>
          </div>
          <span className="text-blue-900">
            <a href="/forgot-password">Forgot password?</a>
          </span>
          <div className="items-center justify-center h-14 w-full my-4 space-x-4 pt-4">
            <Button onClick={loginUser}>Login</Button>
            <span>
              <span>Do not have an account? </span>
              <a className="text-blue-900" href="/sign-up">
                Create one here
              </a>
            </span>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Login;
