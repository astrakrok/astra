import {Route, Routes} from "react-router-dom";
import {page} from "../../constant/page";
import PopupProvider from "../../context/popup/PopupProvider";
import AdminLoginPage from "../page/admin/login/AdminLoginPage";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";
import {LayoutRoutes} from "./LayoutRoutes";

export const AppRoutes = () => {
    return (
        <PermissionBoundary>
            <PopupProvider>
                <Routes>
                    <Route path="/*" element={<LayoutRoutes />} />
                    <Route path={page.admin.login} element={<AdminLoginPage />} />
                </Routes>
            </PopupProvider>
        </PermissionBoundary>
    );
}
