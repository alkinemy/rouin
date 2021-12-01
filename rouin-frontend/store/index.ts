import {combineReducers, Store} from "redux";
import {createWrapper, HYDRATE, MakeStore} from "next-redux-wrapper";
import {TypedUseSelectorHook, useSelector as useReduxSelector} from "react-redux";
import {configureStore} from "@reduxjs/toolkit";
import user from "./user";

export type RootState = ReturnType<typeof rootReducer>


const rootReducer = combineReducers({
    user: user.reducer,
});


let initialRootState: RootState;


const reducer = (state: any, action: any) => {
    if (action.type === HYDRATE) {
        if (state === initialRootState) {
            return {
                ...state,
                ...action.payload,
            };
        }
        return state;
    }
    return rootReducer(state, action);
};


const initStore: MakeStore<Store> = () => {
    const store = configureStore({
        reducer,
        devTools: true,
    });
    initialRootState = store.getState();
    return store;
};


export const useSelector: TypedUseSelectorHook<RootState> = useReduxSelector;

export const wrapper = createWrapper(initStore);