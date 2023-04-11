import {Route, Routes} from "react-router-dom";
import AuthProvider from "../../context/auth/AuthProvider";
import PopupProvider from "../../context/popup/PopupProvider";
import ErrorBoundary from "../ErrorBoundary/ErrorBoundary";
import {LayoutRoutes} from "./LayoutRoutes";
import ProfileBoundary from "../ProfileBoundary/ProfileBoundary";

export const AppRoutes = () => {
    return (
        <AuthProvider>
            <PopupProvider>
                <ErrorBoundary>
                    <ProfileBoundary>
                        <Routes>
                            <Route path="/*" element={<LayoutRoutes />} />
                        </Routes>
                    </ProfileBoundary>
                </ErrorBoundary>
            </PopupProvider>
        </AuthProvider>
    );
}
