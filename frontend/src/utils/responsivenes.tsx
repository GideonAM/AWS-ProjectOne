import { useMediaQuery } from "@mantine/hooks";
import React from "react";

export const responsivenes = () => {
  const isSmallScreen = useMediaQuery("(max-width: 768px)");
  const isMediumScreen = useMediaQuery(
    "(min-width: 769px) and (max-width: 1024px)"
  );
  const isLargeScreen = useMediaQuery("(min-width: 1025px)");

  let textSize, smallTextSize, midText;

  if (isSmallScreen) {
    textSize = "4rem";
    midText = "1.5rem";
    smallTextSize = "2rem";
  } else if (isMediumScreen) {
    textSize = "6rem";
    smallTextSize = "2rem";
    midText = "4rem";
  } else if (isLargeScreen) {
    textSize = "10rem";
    smallTextSize = "4rem";
    midText = "3.1rem";
  }

  return { textSize, smallTextSize, midText };
};
