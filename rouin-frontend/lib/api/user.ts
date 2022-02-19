import axios from "./index";
import {TokenDto, UserDto} from "../../types/dto";

//TODO implement sign in process
const signIn = () => axios.post<UserDto>("/api/v1/users/sign-in", {email: "test@test.com"})


const issueToken = (userId: string) => axios.post<TokenDto>(`/api/v1/users/${userId}/tokens`)


const linkBank = (userId: string, publicToken: string) => axios.post<TokenDto>(`/api/v1/ledgers/${userId}/banks`, {token: publicToken})


export const userApis = {signIn, issueToken, linkBank};
