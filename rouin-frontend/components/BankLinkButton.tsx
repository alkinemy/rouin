import React, {useCallback} from "react";
import {usePlaidLink} from "react-plaid-link";
import Button from "./common/Button";
import {useSelector} from "../store";
import {userApis} from "../lib/api/user";


const BankLinkButton: React.FC = () => {
    const token = useSelector(state => state.user.token);
    const userId = useSelector(state => state.user.userId);
    const onSuccessLink = useCallback((publicToken) => {
        userApis.linkBank(userId, publicToken);
    }, []);
    const config: Parameters<typeof usePlaidLink>[0] = {
        token: token,
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