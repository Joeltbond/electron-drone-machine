(ns synth.core
  (:require [reagent.core :as r]
            [synth.synth :as s]
            [cljsjs.react]))

(def
  app-state
  (r/atom {:switch-states {}}))

(def notes [220.00 246.94 261.63 293.66 329.63 349.23 392.00
            440.00 493.88 523.25 587.33 659.25 698.46 783.99
            880.00])

(defn get-switch-state [freq]
  (get-in @app-state [:switch-states freq] false))

(defn start-note [freq]
  (s/start-note freq))

(defn stop-note [freq]
  (s/stop-note freq))

(defn on-button-click [freq]
  (do
    (swap! app-state update-in [:switch-states freq] not)
    (if (get-switch-state freq)
      (start-note freq)
      (stop-note freq))))

(defn osc-button [freq]
  [:button
   {:on-click #(on-button-click freq)
    :class (if (get-switch-state freq) "on")}])

(defn main-page []
  [:div {:class "container"}
   (for [f notes]
     [osc-button f])])

(defn mount-root []
  (r/render [main-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
