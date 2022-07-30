import useLocalStorage from "../../hook/useLocalStorage";
import AuthContext from "./AuthContext";
import {localStorageKey} from "../../constant/local.storage.key";

const AuthProvider = props => {
    const [userData, setUserData] = useLocalStorage(localStorageKey.userData, null);
    
    return (
        <AuthContext.Provider value={{userData, setUserData}} {...props}>
            {props.children}
        </AuthContext.Provider>
    );
}

export default AuthProvider;
