/**
 * Elune - Lightweight Forum Powered by Razor.
 * Copyright (C) 2017, Touchumind<chinash2010@gmail.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.elune.controller.api;

import com.elune.dal.DBManager;
import com.elune.service.BalanceLogService;
import com.elune.service.UserLogService;
import com.elune.service.UserMetaService;

import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

@RoutePrefix("api/v1/balance")
public class BalanceController extends APIController {

    private DBManager dbManager;

    @FromService
    private UserLogService userLogService;

    @FromService
    private UserMetaService userMetaService;

    @FromService
    private BalanceLogService balanceLogService;

    public BalanceController(DBManager dbManager) {

        this.dbManager = dbManager;
    }

    @HttpGet
    @Route("")
    public void getBalanceDetails(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("order") String order) {
        if (order == null || !(order.toLowerCase().equals("asc"))) {
            order = "DESC";
        }
        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");

            if (uid < 1) {
                throw new HttpException("尚未登录", 401);
            }

            Succeed(balanceLogService.getBalanceLogs(uid, page, pageSize, "id ".concat(order)));
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpGet
    @Route("rank")
    public void getBalanceRank(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("order") String order) {
        if (order == null || !(order.toLowerCase().equals("asc"))) {
            order = "DESC";
        }
        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");

            if (uid < 1) {
                throw new HttpException("尚未登录", 401);
            }

            Succeed(balanceLogService.getBalanceRank(page, pageSize, order));
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpGet
    @Route("costrank")
    public void getBalanceCostRank(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("order") String order) {
        if (order == null || !(order.toLowerCase().equals("asc"))) {
            order = "DESC";
        }
        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");

            if (uid < 1) {
                throw new HttpException("尚未登录", 401);
            }

            Succeed(balanceLogService.getBalanceCostRank(page, pageSize, order));
        } catch (Exception e) {

            Fail(e);
        }
    }
}
