import { useEffect, useState } from "react";
import {useNavigate} from "react-router-dom"
import FileServerEndpoints from "../service/FileServerEndpoints";
import {
  ActionIcon,
  Box,
  Button,
  Container,
  Group,
  Paper,
  Skeleton,
  Text,
  TextInput,
  Modal,
  rem,
  Image,
  SimpleGrid,
  Pagination,
} from "@mantine/core";
import {
  IconArrowRight,
  IconCloudUpload,
  IconSearch,
  IconTrash,
} from "@tabler/icons-react";
import { Link } from "react-router-dom";

const FeedAdmin = ({ user, dispatchUser }) => {
  const [noTransitionOpened, setNoTransitionOpened] = useState(false);
  const [files, setFiles] = useState([]);
  const [search, setSearch] = useState({
    name: "",
  });
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [fileName, setFileName] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const ITEMS_PER_PAGE = 6; // 6 images per page
  const navigate = useNavigate();

  const deleteFile = async (fileId) => {
    try {
      setIsLoading(true);
      const { data } = await FileServerEndpoints.deleteFileById(fileId, user);
      if (files) {
        setFiles((prevFiles) => {
          setMessage(data);
          setIsLoading(false);
          setNoTransitionOpened(false);
          return prevFiles.filter((file) => file.id !== fileId);
        });
        setTimeout(() => setMessage(""), 3000);
      }
    } catch (error) {
      setIsLoading(false);
    }
  };

  const handleChange = (e) => {
    const value = e.target.value;
    setSearch({ ...search, [e.target.name]: value });
  };

  const handleDeleteFile = async (file) => {
    setFileName(file);
    setNoTransitionOpened(true);
  };

  const getFiles = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      if (!search.name) return;
      const { data } = await FileServerEndpoints.adminSearchForFile(
        search.name,
        user
      );
      setFiles(data);
      setIsLoading(false);
    } catch (error) {
      alert(error.message);
      setIsLoading(false);
    }
  };

  const logout = async (e) => {
    e.preventDefault();
    FileServerEndpoints.logout(user)
      .then((response) => {
        dispatchUser({ type: "clear-session", payload: response.data });
        alert(response.data);
        navigate("/login");
      })
      .catch((error) => {
        alert(error.response.data);
      });
  };

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      try {
        const { data } = await FileServerEndpoints.adminGetAllFiles(user);
        setFiles(data);
        setIsLoading(false);
      } catch (error) {
        setIsLoading(false);
      }
    })();
  }, [user]);

  // Pagination logic
  const totalPages = Math.ceil(files.length / ITEMS_PER_PAGE);
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const currentFiles = files.slice(startIndex, endIndex);

  const items = currentFiles.map((row) => (
    <Box key={row.id}>
      <Image src={row.url} alt={row.name} width={100} height={100} />
      <Group>
        <Text>{row.name}</Text>
        <Group>
          <Text
            style={{ cursor: "pointer" }}
            onClick={() => handleDeleteFile(row)}
            fz="xs"
            c="red"
            fw={700}
          >
            Delete
          </Text>
          <IconTrash size={17} color="red" />
        </Group>
      </Group>
    </Box>
  ));

  return (
    <>
      <Modal
        opened={noTransitionOpened}
        onClose={() => setNoTransitionOpened(false)}
        title={
          <Text fw={700}>
            Are you sure you want to delete{" "}
            <span style={{ fontSize: 15, color: "red" }}>
              {fileName?.name}
            </span>
          </Text>
        }
        transitionProps={{
          transition: "fade",
          duration: 600,
          timingFunction: "linear",
        }}
      >
        <Button
          bg={"red"}
          loading={isLoading}
          onClick={() => deleteFile(fileName?.name)}
          style={{ width: "5rem" }}
          c={"white"}
          mr={"2rem"}
        >
          Yes
        </Button>
        <Button c={"white"} onClick={() => setNoTransitionOpened(false)}>
          No
        </Button>
      </Modal>
      <div>
        <div className="container mx-auto p-3">
          <Container>
            <Paper
              elevation={1}
              style={{ display: "flex", justifyContent: "space-between" }}
            >
              <Link to={"/upload-file"}>
                <Button rightSection={<IconCloudUpload />}> Upload File</Button>
              </Link>

              <Link to={"/change-password"}>
                <Button>Change Password</Button>
              </Link>
              <Box>
                <Button onClick={logout}>Logout</Button>
              </Box>
            </Paper>
          </Container>
          <div className="shadow border-b-4">
            <div className="text-red-500 font-medium text-xl">
              {message ? message : ""}
            </div>
          </div>
        </div>
      </div>

      {isLoading ? (
        <Container>
          <Skeleton height={40} mt={10} />
          <Skeleton height={40} mt={10} />
          <Skeleton height={40} mt={10} />
          <Skeleton height={40} mt={10} />
        </Container>
      ) : (
        <Container size={"lg"} mt={20}>
          {files?.length > 0 ? (
            <>
              <SimpleGrid cols={3} spacing="md">
                {items}
              </SimpleGrid>
              {totalPages > 1 && (
                <Pagination
                  total={totalPages}
                  value={currentPage}
                  onChange={setCurrentPage}
                  mt="md"
                />
              )}
            </>
          ) : (
            <Container>
              <Text fw={700} fs={"10rem"} style={{ textAlign: "center" }}>
                Ops!!!!!! No File found
              </Text>
            </Container>
          )}
        </Container>
      )}
    </>
  );
};

export default FeedAdmin;