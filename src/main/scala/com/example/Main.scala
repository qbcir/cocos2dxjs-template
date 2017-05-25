package com.example

import org.cocos2dxjs.cocos2d.core.cocoa.ccSize
import org.cocos2dxjs.cocos2d.core.labelttf.ccLabelTTF
import org.cocos2dxjs.cocos2d.core.layers.{ccLayer, t_ccLayer}

import scalajs.js
import scalajs.js.Dynamic.{global => g}
import org.scalajs.dom.document
import org.cocos2dxjs.{cc, ccgame, ccsys}
import org.cocos2dxjs.cocos2d.core.platform.ccResolutionPolicy
import org.cocos2dxjs.cocos2d.core.scenes.{ccScene}
import org.cocos2dxjs.cocos2d.core.sprites.ccSprite
import org.cocos2dxjs.cocos2d.menus.{ccMenu, ccMenuItemImage}

object Main extends js.JSApp {
  def main(): Unit = {
    val onStart: js.Function0[Unit] = () => {
      g.MyScene = g.cc.Scene.extend(new js.Object {
        val onEnter = new js.ThisFunction0[js.Dynamic, Unit] {
          override def apply(thisArg: js.Dynamic) = {
            thisArg._super()
            val layer = js.Dynamic.newInstance(g.MyLayer)()
            thisArg.addChild(layer.asInstanceOf[ccLayer], 0, "layer")
            layer.init()
          }
        }
      })
      g.MyLayer = g.cc.Layer.extend(new js.Object {
        val onClose: js.Function0[Unit] = () => {
          g.console.log("close")
        }
        val init = new js.ThisFunction0[js.Dynamic, Unit] {
          override def apply(thisArg: js.Dynamic) = {
            thisArg._super()
            val size = cc.director.getWinSize()
            val closeItem = new ccMenuItemImage(
              Resources.s_CloseNormal,
              Resources.s_CloseSelected,
              Resources.s_CloseNormal,
              onClose,
              thisArg.asInstanceOf[t_ccLayer])
            closeItem.setAnchorPoint(0.5f, 0.5f)
            closeItem.setPosition(size.width - 20, 20)
            val menu = new ccMenu(closeItem)
            thisArg.menu = menu
            thisArg.addChild(menu, 1, "menu")
            menu.setPosition(0f, 0f)
            val helloLabel = new ccLabelTTF("Hello World", "Impact", 38, new ccSize(0, 0), 0, 0)
            helloLabel.setPosition(size.width / 2, size.height - 40)
            thisArg.helloLabel = helloLabel
            thisArg.addChild(helloLabel, 5, "helloLabel")
            val sprite = new ccSprite(Resources.s_HelloWorld, null, false)
            sprite.setAnchorPoint(0.5f, 0.5f)
            sprite.setPosition(size.width / 2, size.height / 2)
            sprite.setScaleY(size.height / sprite.getContentSize.height)
            thisArg.sprite = sprite
            thisArg.addChild(sprite, 0, "sprite")
          }
        }
      })
      if (!ccsys.isNative) {
        document.body.removeChild(document.getElementById("cocosLoading"))
      }
      var designSize = cc.size(480f, 800f)
      val screenSize = cc.view.getFrameSize()
      if (!ccsys.isNative && screenSize.height < 800f) {
        designSize = cc.size(320f, 480f)
        cc.loader.resPath = "Normal"
      } else {
        cc.loader.resPath = "HD"
      }
      val onPreloadDone: js.Function0[Unit] = () => {
        cc.director.runScene(js.Dynamic.newInstance(g.MyScene)().asInstanceOf[ccScene])
      }
      cc.view.setDesignResolutionSize(designSize.width, designSize.height, ccResolutionPolicy.SHOW_ALL)
      g.cc.LoaderScene.preload(Resources.resources, onPreloadDone, ccgame)
      ()
    }
    ccgame.run(js.Dynamic.literal(
      id="gameCanvas",
      engineDir="./cocos2d-html5-develop",
      modules=js.Array("cocos2d")
    ), onStart)
  }
}
