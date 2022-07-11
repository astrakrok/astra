import { useEffect, useState } from "react"
import { useLocation } from "react-router-dom";
import { checkPermission } from "../../handler/permission.handler";
import { permissionStatus } from "../../constant/permission.status";
import PrePage from "../page/pre-page/PrePage";
import NotFoundPage from "../page/not-found/NotFoundPage";

const PermissionBoundary = ({children}) => {
    const [status, setStatus] = useState(permissionStatus.processing);
    const location = useLocation();

    useEffect(() => {
        const permissionResult = checkPermission(location.pathname);
        setStatus(permissionResult ? permissionStatus.allowed : permissionStatus.denied);
    }, [location]);

    if (status === permissionStatus.processing) {
        return (
            <PrePage />
        );
    } else if (status === permissionStatus.denied) {
        return (
            <NotFoundPage />
        );
    }
    return children;
}

export default PermissionBoundary;
