import axios from "./index";

//TODO implement sign in process
const signIn = () => axios.post("/api/v1/users/sign-in", {email: "test@test.com"})

const issueLinkToken = (userId: string) => axios.post(`/api/v1/users/${userId}/tokens/links`)

const registerPublicToken = (userId: string, publicToken: string) => axios.post(`/api/v1/users/${userId}/tokens/public`, {token: publicToken})

export const userApis = {signIn, issueLinkToken, registerPublicToken};
