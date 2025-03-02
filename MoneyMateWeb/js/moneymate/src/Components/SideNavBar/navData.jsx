import { TbArrowsExchange, TbCategory2 } from "react-icons/tb";
import { IoMdCube } from "react-icons/io";
import {RiUserFill, RiShutDownLine, RiCheckboxMultipleBlankLine} from "react-icons/ri";
import { FaChartPie } from "react-icons/fa";
import { BsCreditCardFill } from "react-icons/bs";
import {HiOutlinePlusSm} from "react-icons/all.js";


export const navItems = [
    {
        icon: <BsCreditCardFill />,
        text: "Wallets",
        link: "/wallets"
    },
    {
        icon: <FaChartPie />,
        text: "Private Statistics",
        link: `/statistics/wallets`
    },
    {
        icon: <RiCheckboxMultipleBlankLine />,
        text: "Shared Statistics",
        link: `/statistics/sharedWallets`
    },
    {
        icon: <RiUserFill />,
        text: "Profile",
        link: "/profile"
    },
    {
        icon: <HiOutlinePlusSm style={{ fontSize: "35px" }}/>,
        text: "Create Transaction",
        link: null
    },
    {
        icon: <TbCategory2 />,
        text: "Categories",
        link: "/categories"
    },
    {
        icon: <RiShutDownLine />,
        text: "Logout",
        link: "/logout"
    }
];
