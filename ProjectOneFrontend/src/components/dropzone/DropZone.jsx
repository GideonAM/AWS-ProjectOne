import { useEffect, useRef, useState } from "react";
import {
  Text,
  Group,
  Button,
  rem,
  useMantineTheme,
  Image,
  SimpleGrid,
} from "@mantine/core";
import { Dropzone } from "@mantine/dropzone";
import { IconCloudUpload, IconX, IconDownload } from "@tabler/icons-react";
import PropTypes from "prop-types";
export function DropzoneButton({ setFile }) {
  const [k, y] = useState([]);
  const theme = useMantineTheme();
  const openRef = useRef(null);

  DropzoneButton.propTypes = {
    setFile: PropTypes.func.isRequired,
    file: PropTypes.func.isRequired,
  };
  const previews = k?.map((file, index) => {
    const imageUrl = URL?.createObjectURL(file);
    if (file.type.includes("image")) {
      return (
        <Image
          key={index}
          src={imageUrl}
          height={"400"}
          width={"599"}
          onLoad={() => URL.revokeObjectURL(imageUrl)}
        />
      );
    } else {
      return (
        <div style={{ width: 400 }} key={index}>
          {file.name} selected successfully
        </div>
      );
    }
  });
  useEffect(() => {
    if (k.length === 0) return;
    setFile(k);
  }, [k]);

  return (
    <div style={{ position: "relative", marginBottom: rem(30) }}>
      <>
        <Dropzone
          openRef={openRef}
          onDrop={y}
          style={{ borderWidth: rem(1), paddingBottom: rem(50) }}
          radius="md"
        >
          <div style={{ pointerEvents: "none" }}>
            <Group justify="center">
              <Dropzone.Accept>
                <IconDownload
                  style={{ width: rem(50), height: rem(50) }}
                  color={theme.colors.blue[6]}
                  stroke={1.5}
                />
              </Dropzone.Accept>
              <Dropzone.Reject>
                <IconX
                  style={{ width: rem(50), height: rem(50) }}
                  color={theme.colors.red[6]}
                  stroke={1.5}
                />
              </Dropzone.Reject>
              <Dropzone.Idle>
                <IconCloudUpload
                  style={{ width: rem(50), height: rem(50) }}
                  stroke={1.5}
                />
              </Dropzone.Idle>
            </Group>

            <Text ta="center" fw={700} fz="lg" mt="xl">
              <Dropzone.Accept>Drop files here</Dropzone.Accept>
              <Dropzone.Reject> File less than 30mb</Dropzone.Reject>
              <Dropzone.Idle>Upload File</Dropzone.Idle>
            </Text>
            <Text ta="center" fz="sm" mt="xs" c="dimmed">
              Drag&apos;n&apos;drop files here to upload. We can accept{" "}
              <i>files</i> that are less than 25mb in size.
            </Text>
          </div>
        </Dropzone>
        <Button
          style={{
            position: "absolute",
            left: "50%",
            top: "98%",
            transform: "translate(-50%, -50%)",
          }}
          size="md"
          radius="xl"
          onClick={() => openRef.current?.()}
        >
          Select files
        </Button>
      </>
      <SimpleGrid
        cols={{ base: 1, sm: 4 }}
        mt={previews?.length > 0 ? "xl" : 0}
      >
        {previews}
      </SimpleGrid>
    </div>
  );
}
