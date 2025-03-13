import "@mantine/core/styles.css";
import "@mantine/dropzone/styles.css";
import "@mantine/notifications/styles.css";
import { MantineProvider } from "@mantine/core";
import { Notifications } from "@mantine/notifications";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import DashBoard from "./pages/DashBoard";
import SignUp from "./pages/SignUp";
import Login from "./pages/Login";
import ForgotPassword from "./pages/ForgotPassword";
import UpdatePassword from "./pages/UpdatePassword";
import FileUpload from "./pages/FileUpload";
import { HomePage } from "./pages/HomePage";
import { useReducer } from "react";
import ChangePassword from "./pages/ChangePassword";

const updateUser = (state, action) => {
  switch (action.type) {
    case "update-session": {
      return {
        userEmail: action.payload.userEmail,
        userId: action.payload.userId,
        jwt: action.payload.jwt,
        isAdmin: action.payload.admin,
      };
    }
    case "clear-session": {
      return {
        userId: "",
        userEmail: "",
        jwt: "null",
        isAdmin: null,
      };
    }
    default:
      return state;
  }
};

const initialUserInfo = {
  userId: "",
  userEmail: "",
  jwt: "",
  isAdmin: null,
};

function App() {
  const [user, dispatchUser] = useReducer(updateUser, initialUserInfo);

  return (
    <MantineProvider>
      <Notifications />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route
            path="/admin"
            element={<DashBoard user={user} dispatchUser={dispatchUser} />}
          />
          <Route path="/sign-up" element={<SignUp />} />
          <Route
            path="/login"
            element={<Login dispatchUser={dispatchUser} />}
          />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route
            path="/change-password"
            element={<ChangePassword user={user} />}
          />
          <Route path="/update-password/:token" element={<UpdatePassword />} />
          <Route path="/upload-file" element={<FileUpload user={user} />} />
        </Routes>
      </BrowserRouter>
    </MantineProvider>
  );
}

export default App;
