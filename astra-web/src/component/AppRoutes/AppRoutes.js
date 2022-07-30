import {Route, Routes} from "react-router-dom";
import {page} from "../../constant/page";
import AuthProvider from "../../context/auth/AuthProvider";
import PopupProvider from "../../context/popup/PopupProvider";
import AxiosClient from "../AxiosClient/AxiosClient";
import AdminLoginPage from "../page/admin/login/AdminLoginPage";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";
import {LayoutRoutes} from "./LayoutRoutes";

export const AppRoutes = () => {
    return (
        <AuthProvider>
            <PopupProvider>
                <AxiosClient>
                    <PermissionBoundary>
                        <Routes>
                            <Route path="/*" element={<LayoutRoutes />} />
                            <Route path={page.admin.login} element={<AdminLoginPage />} />
                        </Routes>
                    </PermissionBoundary>
                </AxiosClient>
            </PopupProvider>
        </AuthProvider>
    );
}
