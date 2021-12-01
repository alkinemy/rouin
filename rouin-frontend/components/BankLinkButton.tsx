import React, {useCallback, useEffect} from "react";
import {usePlaidLink} from "react-plaid-link";
import Button from "./common/Button";
import {useSelector} from "../store";
import {userApis} from "../lib/api/user";


const BankLinkButton: React.FC = () => {
    const linkToken = useSelector(state => state.user.linkToken);
    const userId = useSelector(state => state.user.userId);
    const onSuccessLink = useCallback((publicToken) => {
        userApis.registerPublicToken(userId, publicToken)
    }, []);
    const config: Parameters<typeof usePlaidLink>[0] = {
        token: linkToken,
        onSuccess: onSuccessLink,
    };
    const {open, ready} = usePlaidLink(config);
    return (
        <Button onClick={() => open()} disabled={!ready}>
            Link banks
        </Button>
    );
}

export default BankLinkButton;