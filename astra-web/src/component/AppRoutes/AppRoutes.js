import {Route, Routes} from "react-router-dom";
import {page} from "../../constant/page";
import AuthProvider from "../../context/auth/AuthProvider";
import PopupProvider from "../../context/popup/PopupProvider";
import {save} from "../../handler/token.handler";
import AxiosClient from "../AxiosClient/AxiosClient";
import AdminLoginPage from "../page/admin/login/AdminLoginPage";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";
import ErrorBoundary from "../ErrorBoundary/ErrorBoundary";
import {LayoutRoutes} from "./LayoutRoutes";

export const AppRoutes = () => {
    // TODO remove after Auth0 configuration
    save({
        accessToken: "mockAccessToken",
        refreshToken: "mockRefreshToken"
    });

    return (
        <AuthProvider>
            <PopupProvider>
                <ErrorBoundary>
                    <AxiosClient>
                        <PermissionBoundary>
                            <Routes>
                                <Route path="/*" element={<LayoutRoutes />} />
                                <Route path={page.admin.login} element={<AdminLoginPage />} />
                            </Routes>
                        </PermissionBoundary>
                    </AxiosClient>
                </ErrorBoundary>
            </PopupProvider>
        </AuthProvider>
    );
}
