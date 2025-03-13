import { useState } from "react";
import FileServerEndpoints from "../service/FileServerEndpoints";
import { DropzoneButton } from "../components/dropzone/DropZone";
import {
  Button,
  Container,
  Flex,
  Image,
  Input,
  LoadingOverlay,
  Text,
  Textarea,
} from "@mantine/core";
import { Link } from "react-router-dom";
import { IconArrowBack } from "@tabler/icons-react";
import fileImage from "../../public/images/home-hero.webp";
import { notifications } from "@mantine/notifications";
import { responsivenes } from "../utils/responsivenes";

const FileUpload = () => {
  const [fileBody, setFilebody] = useState({
    title: "",
    description: "",
  });
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const { midText } = responsivenes();

  const handleChange = (e) => {
    const value = e.target.value;
    setFilebody({ ...fileBody, [e.target.name]: value });
  };

  const uploadFile = async (e) => {
    e.preventDefault();
    fileBody.file = file[0];
    let formData = new FormData();
//     formData.append("title", fileBody.title);
//     formData.append("description", fileBody.description);
    formData.append("file", file[0]);

    try {
      setIsLoading(true);
      const { data } =
        (await FileServerEndpoints.uploadFile(formData)) || {};
      setMessage(data);
      setIsLoading(false);
      notifications?.show({
        title: "Success",
        message: data,
        color: "green",
      });
    } catch (error) {
      const { data } = error?.response || {};
      setIsLoading(false);
      notifications?.show({
        title: "Oh No!!!",
        message: data || "Something went wrong! 🧐🧐🧐🧐",
        color: "red",
      });
    }
  };

  return (
    <Container>
      <LoadingOverlay
        visible={isLoading}
        zIndex={1000}
        overlayProps={{ radius: "sm", blur: 2 }}
      />
      <form>
        <div className="m-5">
          <Text
            variant="gradient"
            gradient={{ from: "orange", to: "pink", deg: 90 }}
            fw={700}
            size={midText}
          >
            Hey there! Welcome aboard 🚢🚢🚢
          </Text>
          <Text fw={700} fz={20}>
            It looks like you are ready to upload some files, Load them in and
            let explore together🚀🚀🚀
          </Text>
          <Image src={fileImage} />
          <div className="mx-auto max-w-2xl font-bold text-xl">
            <div>{message ? message : ""}</div>
            <div hidden={true} className="my-5">
              <label>Title: </label>
              <Input
                onChange={(e) => handleChange(e)}
                placeholder="title of file"
                name="title"
                value={fileBody.title}
              />
            </div>
            <div hidden={true} className="my-5 ">
              <label>Description: </label>
              <Textarea
                onChange={(e) => handleChange(e)}
                placeholder="description of file"
                name="description"
                minRows={2}
                maxRows={4}
                value={fileBody.description}
              />
            </div>
            <DropzoneButton setFile={setFile} />
            <Flex justify={"center"}>
              <Button onClick={uploadFile} mr={10}>
                Upload
              </Button>
              <Link to={"/"}>
                <Button
                  variant="light"
                  leftSection={<IconArrowBack size={14} />}
                >
                  Back to Files
                </Button>
              </Link>
            </Flex>
          </div>
        </div>
      </form>
    </Container>
  );
};

export default FileUpload;
