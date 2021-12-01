import 'tailwindcss/tailwind.css'
import App, {AppContext, AppProps} from "next/app";
import Header from "../components/Header";
import {wrapper} from "../store";
import {userActions} from "../store/user";
import {userApis} from "../lib/api/user";
import {Store} from "redux";

const app = ({Component, pageProps}: AppProps) => {
    return (
        <>
            <Header/>
            <Component {...pageProps} />
        </>
    );
};

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

const issueLinkTokenAndDispatch = async (store: Store, userId: string) => {
    try {
        const {data: linkToken} = await userApis.issueLinkToken(userId);
        store.dispatch(userActions.setLinkToken(linkToken.token));
    } catch (e) {
        //TODO error handling
        // console.log("Fail to sign in", e);
    }
}

app.getInitialProps = wrapper.getInitialAppProps(store => async (context: AppContext) => {
    const appInitialProps = await App.getInitialProps(context);
    const user = await signInAndDispatch(store);
    await issueLinkTokenAndDispatch(store, user.userId);
    return {...appInitialProps};
});

export default wrapper.withRedux(app);