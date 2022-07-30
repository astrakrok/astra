import {Navigate} from "react-router-dom";
import {localStorageKey} from "../../../constant/local.storage.key";
import {page} from "../../../constant/page";
import {removeItem} from "../../../handler/storage.handler";

const LogoutPage = () => {
    removeItem(localStorageKey.localData);

    return (
        <Navigate to={page.home} replace />
    );
}

export default LogoutPage;
