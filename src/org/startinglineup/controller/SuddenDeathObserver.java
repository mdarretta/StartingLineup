package org.startinglineup.controller;

import java.util.Observer;

/**
 * Observer used to indicate that a game is now in sudden death; that is,
 * it is the bottom of the ninth onward.
 * 
 * @author Mike Darretta
 */
interface SuddenDeathObserver extends Observer {}