import React from "react";

import "./App.css";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { ExploreTopBooks } from "./layouts/Homepage/Components/ExploreTopBooks";
import { Carousel } from "./layouts/Homepage/Components/Carousel";
import { Heros } from "./layouts/Homepage/Components/Heros";
import { LibraryServices } from "./layouts/Homepage/Components/LibraryServices";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { HomePage } from "./layouts/Homepage/HomePage";
import { SearchBooksPage } from "./layouts/searchBooksPage/SearchBooksPage";
import { Redirect, Route, Switch ,useHistory} from "react-router-dom";
import { BookCheckOutPage } from "./layouts/BookCheckout/BookCheckOutPage";
import { oktaConfig } from "./lib/oktaConfig";
import {OktaAuth,toRelativeUrl} from '@okta/okta-auth-js';
import { LoginCallback, SecureRoute, Security } from "@okta/okta-react";
import LoginWidget from "./auth/LoginWidget";
import { ReviewListPage } from "./layouts/BookCheckout/ReviewListPage/ReviewListPage";
import { ShelfPage } from "./layouts/ShelfPage/ShelfPage";
import { MessagesPage } from "./layouts/MessagesPage/MessagesPage";
import { ManageLibraryPage } from "./layouts/ManageLibraryPage/ManageLibraryPage";

const oktaAuth = new OktaAuth(oktaConfig);

export const App = () => {

  const customAuthHandler = () => {
    history.push('/login');
  }

  const history = useHistory();

  const restoreOriginalUri = async (_oktaAuth : any, originalUri : any) => {
    history.replace(toRelativeUrl(originalUri || '/', window.location.origin))
  }

  return (
    <div className="d-flex flex-column min-vh-100">
      <Security oktaAuth={oktaAuth} restoreOriginalUri={restoreOriginalUri} onAuthRequired={customAuthHandler}>
      <Navbar />
      <div className="flex-grow-1">
      <Switch>
        <Route path="/" exact>
          <Redirect to='/home' />
        </Route>
        <Route path='/home'>
        <HomePage />
        </Route>

        <Route path="/search">
          <SearchBooksPage />
        </Route>
        <Route path='/reviewlist/:bookId'>
           <ReviewListPage/>
        </Route>
        <Route path='/checkout/:bookId'>
          < BookCheckOutPage />
        </Route>
        <Route path='/login' render={()=> <LoginWidget config={oktaConfig}/>} />
        <Route path='/login/callback' component={LoginCallback} />
       <SecureRoute path='/shelf'><ShelfPage/></SecureRoute>
       <SecureRoute path='/messages'><MessagesPage/></SecureRoute>
       <SecureRoute path='/admin'><ManageLibraryPage/></SecureRoute>
      </Switch>
      </div>
      <Footer />
      </Security>
    </div>
  );
};