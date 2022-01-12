import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import NavbarComponent from "./components/navbar/NavbarComponent";
import HomeLayout from "./layouts/HomeLayout";
import LoginLayout from "./layouts/LoginLayout";
import BrowseLayout from "./layouts/BrowseLayout";
import EditArticle from "./components/crud/EditArticleComponent";
import AddArticle from "./components/crud/AddArticleComponent";
import {PrivateRoute} from "./components/PrivateRoute";
import NotFound from "./layouts/NotFound";
import Register from "./components/crud/RegisterComponent";
import UserProfile from "./components/view/UserProfile";
import AdminLayout from "./layouts/AdminLayout";
import SellerLayout from "./layouts/SellerLayout";
import CartItemsTable from "./components/tables/CartItemsTable";
import SearchArticlesTable from "./components/tables/SearchArticlesTable";
import CommentsElasticTable from "./components/tables/SearchCommentsTable";
import OrderTable from "./components/tables/OrderTable";
import SellerProfile from "./components/view/SellerProfile";
import {DiscountComponent} from "./components/crud/DiscountComponent";
import DiscountLayout from "./layouts/DiscountLayout";

function App() {
    return (
        <div className="App">
            <NavbarComponent/>
            <Router>
                <Switch>
                    <Route path="/home" exact component={HomeLayout}/>
                    <Route path="/register" exact component={Register}/>
                    <Route path="/login" exact component={LoginLayout}/>
                    <Route path="/profile" exact component={UserProfile}/>
                    <PrivateRoute
                        path="/sellers"
                        exact
                        component={SellerLayout}
                        roles={["ROLE_BUYER"]}/>
                    <PrivateRoute
                        path="/cart"
                        exact
                        component={CartItemsTable}
                        roles={["ROLE_BUYER"]}/>
                    <PrivateRoute
                        path="/orders"
                        exact
                        component={OrderTable}
                        roles={["ROLE_BUYER"]}/>
                    <PrivateRoute
                        path="/search"
                        exact
                        component={SearchArticlesTable}
                        roles={["ROLE_BUYER"]}/>
                    <PrivateRoute
                        path="/addArticle"
                        exact
                        component={AddArticle}
                        roles={["ROLE_SELLER"]}/>
                    <PrivateRoute
                        path="/discounts"
                        exact
                        component={DiscountComponent}
                        roles={["ROLE_SELLER"]}/>
                    <PrivateRoute
                        path="/discounts/management"
                        exact
                        component={DiscountLayout}
                        roles={["ROLE_SELLER"]}/>
                    <PrivateRoute
                        path="/editArticle/:id"
                        exact
                        component={EditArticle}
                        roles={["ROLE_SELLER"]}/>
                    <PrivateRoute
                        path="/seller/:username"
                        exact
                        component={SellerProfile}
                        roles={["ROLE_SELLER", "ROLE_BUYER"]}/>
                    <PrivateRoute
                        path="/users"
                        exact
                        component={AdminLayout}
                        roles={["ROLE_ADMIN"]}/>
                    <PrivateRoute
                        path="/search/comments"
                        exact
                        component={CommentsElasticTable}
                        roles={["ROLE_ADMIN"]}/>
                    <Route path="/browse/:id" exact component={BrowseLayout}/>
                    <Route component={NotFound}/>
                </Switch>
            </Router>
        </div>
    );
}

export default App;
