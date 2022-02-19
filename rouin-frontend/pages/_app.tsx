import "tailwindcss/tailwind.css"
import "../styles/global.css"
import App, {AppContext, AppProps} from "next/app";
import Header from "../components/Header";
import {wrapper} from "../store";
import {userActions} from "../store/user";
import {userApis} from "../lib/api/user";
import {Store} from "redux";

const signInAndDispatch = async (store: Store) => {
    try {
        const {data: user} = await userApis.signIn();
        store.dispatch(userActions.setUser(user));
        return user;
    } catch (e) {
        //TODO error handling
        console.log("Fail to sign in", e);
        return null;
    }
}

const issueTokenAndDispatch = async (store: Store, userId: string) => {
    try {
        const {data: token} = await userApis.issueToken(userId);
        store.dispatch(userActions.setToken(token.token));
    } catch (e) {
        //TODO error handling
        // console.log("Fail to sign in", e);
    }
}

const app = ({Component, pageProps}: AppProps) => {
    return (
        <>
            <Header/>
            <Component {...pageProps} />
        </>
    );
};

app.getInitialProps = wrapper.getInitialAppProps(store => async (context: AppContext) => {
    const appInitialProps = await App.getInitialProps(context);
    const user = await signInAndDispatch(store);
    await issueTokenAndDispatch(store, user.userId);
    return {...appInitialProps};
});

export default wrapper.withRedux(app);