import { createRouter, createWebHistory } from "vue-router";

import MainView from "../views/MainView.vue";

import AttractionView from "@/views/AttractionView.vue";

import PlanView from "@/views/PlanView.vue";
import PlanList from "@/components/plan/PlanList.vue";
import PlanDetail from "@/components/plan/PlanDetail.vue";
import PlanAllList from "@/components/planAll/PlanAllList.vue";

import BragOfHotPlaceView from "@/views/BragOfHotPlaceView.vue";
import HotPlaceList from "@/components/hotPlace/HotPlaceList.vue";
import HotPlacePostCreate from "@/components/hotPlace/HotPlacePostCreate.vue";
import HotPlaceDetail from "@/components/hotPlace/HotPlaceDetail.vue";
import HotPlaceUpdate from "@/components/hotPlace/HotPlaceUpdate.vue";
/*



*/

import TripInfoShareView from "@/views/TripInfoShareView.vue";
import TripInfoShareCreate from "@/components/tripInfo/TripInfoShareCreate.vue";
import TripInfoShareList from "@/components/tripInfo/TripInfoShareList.vue";
import TripInfoShareDetail from "@/components/tripInfo/TripInfoShareDetail.vue";
import TripInfoShareUpdate from "@/components/tripInfo/TripInfoShareUpdate.vue";

import LoginView from "@/views/LoginView.vue";
import MemberFormView from "@/views/MemberFormView.vue";
import MemberDetail from "@/components/member/MemberDetail.vue";
import MemberUpdate from "@/components/member/MemberUpdate.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/attraction",
      name: "attraction",
      component: AttractionView,
    },
    {
      path: "/plan",
      name: "plan",
      component: PlanView,
      redirect: { name: "planList" },
      children: [
        {
          path: "planList",
          name: "planList",
          component: PlanList,
        },
        {
          path: "planDetail/:planId",
          name: "planDetail",
          component: PlanDetail,
        }
      ],
    },
    {
      path: "/planAll",
      name: "planAll",
      component: PlanAllList,
      redirect: { name: "planAllList" },
      children: [
        {
          path: "planAllList",
          name: "planAllList",
          component: PlanAllList,
        }
      ],
    },
    {
      path: "/bragOfHotPlace",
      name: "bragOfHotPlace",
      component: BragOfHotPlaceView,
      redirect: { name: "hotPlaceList" },
      children: [
        {
          path: "hotPlaceList",
          name: "hotPlaceList",
          component: HotPlaceList,
        },
        {
          path: "hotPlacePostCreate",
          name: "hotPlacePostCreate",
          component: HotPlacePostCreate,
        },
        {
          path: "hotPlaceDetail/:id",
          name: "hotPlaceDetail",
          component: HotPlaceDetail,
        },
        {
          path: "hotPlaceupdate/:id",
          name: "hotPlaceUpdate",
          component: HotPlaceUpdate,
        },
      ],
    },
    {
      path: "/tripInfoShare",
      name: "tripInfoShare",
      component: TripInfoShareView,
      redirect: { name: "tripInfoShareList" },
      children: [
        {
          path: "tripInfoShareCreate",
          name: "tripInfoShareCreate",
          component: TripInfoShareCreate,
        },
        {
          path: "tripInfoShareList",
          name: "tripInfoShareList",
          component: TripInfoShareList,
        },
        {
          path: "tripInfoShareDetail/:id",
          name: "tripInfoShareDetail",
          component: TripInfoShareDetail,
        },
        {
          path: "update/:id",
          name: "tripInfoShareUpdate",
          component: TripInfoShareUpdate,
        },
      ],
    },
    {
      path: "/memberForm",
      name: "memberForm",
      component: MemberFormView,
      redirect: { name: "memberDetail" },
      children: [
        {
          path: "memberDetail",
          name: "memberDetail",
          component: MemberDetail,
        },
        {
          path: "memberUpdate",
          name: "memberUpdate",
          component: MemberUpdate,
        }
      ]
    }
  ],
});

export default router;
