import { useState } from "react";
import FileServerEndpoints from "../service/FileServerEndpoints";
import { Button, Input } from "@mantine/core";

const SignUp = () => {
  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const [message, setMessage] = useState("");

  const signUpUser = (e) => {
    e.preventDefault();
    FileServerEndpoints.signup(user)
      .then((response) => {
        alert(response.data);
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
            <h1>File Server Sign Up</h1>
          </div>
          <div className="text-xl text-red-500">{message ? message : ""}</div>
          <div className="items-center justify-center h-14 w-full my-4">
            <label className="font-semibold text-xl">Email:</label>
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
          <div className="items-center justify-center h-14 w-full my-4 space-x-4 pt-4">
            <Button onClick={signUpUser}>Sign up</Button>
            <span>
              <span>Already have an account? </span>
              <a className="text-blue-900" href="/login">
                Login here
              </a>
            </span>
          </div>
        </div>
      </form>
    </div>
  );
};

export default SignUp;
