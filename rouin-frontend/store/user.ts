import {UserState} from "../types/redux";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {UserDto} from "../types/dto";


const initialState: UserState = {
    userId: null,
    email: null,
    linkToken: null,
};


const user = createSlice({
    name: "user",
    initialState,
    reducers: {
        setLinkToken: (state, action: PayloadAction<string>) => {
            state.linkToken = action.payload;
            return state;
        },
        setUser: (state, action: PayloadAction<UserDto>) => {
            state.userId = action.payload.userId;
            state.email = action.payload.email;
            return state;
        }
    },
});


export const userActions = {...user.actions};


export default user;