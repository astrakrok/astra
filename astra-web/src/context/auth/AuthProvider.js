import useLocalStorage from "../../hook/useLocalStorage";
import AuthContext from "./AuthContext";
import {localStorageKey} from "../../constant/local.storage.key";
import {defaultUser} from "../../data/default/user";

const AuthProvider = props => {
    const [userData, setUserData] = useLocalStorage(localStorageKey.userData, defaultUser);
    
    return (
        <AuthContext.Provider value={{userData, setUserData}} {...props}>
            {props.children}
        </AuthContext.Provider>
    );
}

export default AuthProvider;
