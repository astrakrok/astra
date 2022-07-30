import {useLocation} from "react-router-dom";
import {checkPermission} from "../../handler/permission.handler";
import NotFoundPage from "../page/not-found/NotFoundPage";
import AuthConsumer from "../../context/auth/AuthConsumer";

const PermissionBoundary = ({children}) => {
    const location = useLocation();

    return (
        <AuthConsumer>
            {
                ({userData}) => {
                    const permissionResult = checkPermission(location.pathname, userData);
                    if (!permissionResult) {
                        return (<NotFoundPage />);
                    }
                    return (
                        <>
                            {children}
                        </>
                    );
                }
            }
        </AuthConsumer>
    );
}

export default PermissionBoundary;
