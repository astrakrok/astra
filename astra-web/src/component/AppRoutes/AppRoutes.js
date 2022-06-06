import { Routes, Route } from "react-router-dom";
import { LayoutRoutes } from "./LayoutRoutes";

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/*" element={<LayoutRoutes />} />
        </Routes>
    );
}
