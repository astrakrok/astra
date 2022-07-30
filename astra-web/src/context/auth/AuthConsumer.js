import AuthContext from "./AuthContext";

const AuthConsumer = ({children}) => {
    return (
        <AuthContext.Consumer>
            {context => {
                if (context === undefined) {
                    throw new Error('AuthConsumer must be used within a AuthProvider')
                }
                return children(context)
            }}
        </AuthContext.Consumer>
    );
}

export default AuthConsumer;
