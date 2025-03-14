import "@mantine/core/styles.css";
import "@mantine/dropzone/styles.css";
import "@mantine/notifications/styles.css";
import { MantineProvider } from "@mantine/core";
import { Notifications } from "@mantine/notifications";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import DashBoard from "./pages/DashBoard";
import FileUpload from "./pages/FileUpload";
import { HomePage } from "./pages/HomePage";
import { useReducer } from "react";

function App() {

  return (
    <MantineProvider>
      <Notifications />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<DashBoard />} />
{/*           <Route */}
{/*             path="/admin" */}
{/*             element={<DashBoard />} */}
{/*           /> */}
          <Route path="/upload-file" element={<FileUpload />} />
        </Routes>
      </BrowserRouter>
    </MantineProvider>
  );
}

export default App;
